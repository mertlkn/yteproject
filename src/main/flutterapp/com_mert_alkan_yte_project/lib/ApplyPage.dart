import 'dart:convert';

import 'package:com_mert_alkan_yte_project/MapsPage.dart';
import 'package:com_mert_alkan_yte_project/MyIp.dart';
import 'package:com_mert_alkan_yte_project/ReusableCard.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart';
import 'package:com_mert_alkan_yte_project/DataBaseHelper.dart';
import 'package:stomp_dart_client/stomp.dart';
import 'package:stomp_dart_client/stomp_config.dart';
import 'package:stomp_dart_client/stomp_frame.dart';
import 'SuccessfulPage.dart';
import 'package:com_mert_alkan_yte_project/MyIp.dart';

class ApplyPage extends StatefulWidget {
  ApplyPage(this.eventName, this.eventStartTime, this.eventEndTime, this.lat,
      this.lng);

  final String eventName;
  final String eventStartTime;
  final String eventEndTime;
  final double lat;
  final double lng;

  @override
  _ApplyPageState createState() => _ApplyPageState();
}

class _ApplyPageState extends State<ApplyPage> {
  String name;
  String surname;
  String tcKimlikNo;
  String email;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          ReusableCard(
            colour: Colors.blueGrey,
            cardChild: TextField(
              decoration: InputDecoration(hintText: 'Name'),
              onChanged: (input) {
                setState(() {
                  name = input;
                });
              },
            ),
          ),
          ReusableCard(
            colour: Colors.blueGrey,
            cardChild: TextField(
              decoration: InputDecoration(hintText: 'Surname'),
              onChanged: (input) {
                setState(() {
                  surname = input;
                });
              },
            ),
          ),
          ReusableCard(
            colour: Colors.blueGrey,
            cardChild: TextField(
              decoration: InputDecoration(hintText: 'TC Kimlik No'),
              onChanged: (input) {
                setState(() {
                  tcKimlikNo = input;
                });
              },
            ),
          ),
          ReusableCard(
            colour: Colors.blueGrey,
            cardChild: TextField(
              decoration: InputDecoration(hintText: 'Email'),
              onChanged: (input) {
                setState(() {
                  email = input;
                });
              },
            ),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              FlatButton(
                color: Colors.green,
                child: Row(
                  children: [Icon(Icons.send), Text('Apply')],
                ),
                onPressed: () async => await apply(
                    widget.eventName,
                    name,
                    surname,
                    tcKimlikNo,
                    context,
                    widget.lat,
                    widget.lng,
                    widget.eventStartTime,
                    widget.eventEndTime,email),
              ),
              FlatButton(
                color: Colors.blue,
                child: Row(
                  children: [Icon(Icons.map), Text('Show on map')],
                ),
                onPressed: () => Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => MapsPage(widget.lat, widget.lng),
                  ),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Future apply(
      eventName,
      String name,
      String surname,
      String tcKimlikNo,
      BuildContext context,
      double lat,
      double lng,
      String eventStartTime,
      String eventEndTime,String email) async {
    StompClient client = StompClient(
        config: StompConfig.SockJS(
            url: '$myIp/gs-guide-websocket',
            onConnect: onConnectCallback
        )
    );
    client.activate();
    await post('$myIp/applyUser/' + eventName,
        headers: {
          "content-type": "application/json",
        },
        body: jsonEncode(
            {'name': name, 'surname': surname, 'tcKimlikNo': tcKimlikNo})).then(
      (value) async {
        var response = jsonDecode(value.body);
        if (response['messageType'] == 'SUCCESS') {
          _save(eventName, eventStartTime, eventEndTime, lat, lng, name,
              surname, tcKimlikNo);
          get('$myIp/email?to='+email+"&eventName="+eventName+"&name="+name+"&surname="+surname+"&tcKimlikNo="+tcKimlikNo);
          client.send(destination: '/app/hello', body: jsonEncode({
            'eventName': eventName,
            'name': name,
            'surname': surname,
            'tcKimlikNo': tcKimlikNo
          }), headers: {});
          return Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) =>
                  SuccessfulPage(eventName, name, surname, tcKimlikNo),
            ),
          );
          client.send(destination: '/foo/bar', body: 'Your message body', headers: {});
        } else
          return _showMyDialog(context, response['message']);
      },
    );
  }
}

void onConnectCallback(StompClient client, StompFrame connectFrame) {
  print("connected");
}

Future<void> _showMyDialog(BuildContext context, String message) async {
  return showDialog<void>(
    context: context,
    barrierDismissible: false, // user must tap button!
    builder: (BuildContext context) {
      return AlertDialog(
        title: Text('Error'),
        content: SingleChildScrollView(
          child: ListBody(
            children: <Widget>[
              Text(message),
            ],
          ),
        ),
        actions: <Widget>[
          FlatButton(
            child: Text('OK'),
            onPressed: () {
              Navigator.of(context).pop();
            },
          ),
        ],
      );
    },
  );
}

_save(String eventName, String eventStartTime, String eventEndTime, double lat,
    double lng, String name, String surname, String tcKimlikNo) async {
  Events event = Events();
  event.eventName = eventName;
  event.eventStartTime = eventStartTime;
  event.eventEndTime = eventEndTime;
  event.lat = lat;
  event.lng = lng;
  event.name = name;
  event.surname = surname;
  event.tcKimlikNo = tcKimlikNo;
  DatabaseHelper helper = DatabaseHelper.instance;
  print(event.eventName);
  int id = await helper.insert(event);
  print('inserted row: $id');
}
