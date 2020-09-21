import 'package:com_mert_alkan_yte_project/MyIp.dart';
import 'package:flutter/material.dart';
import 'package:com_mert_alkan_yte_project/ApplyPage.dart';
import 'package:http/http.dart';
import 'dart:convert';
import 'package:com_mert_alkan_yte_project/ReusableCard.dart';

class EventsList extends StatefulWidget {
  EventsList({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _EventsListState createState() => _EventsListState();
}

class _EventsListState extends State<EventsList> {
  var events = [];

  @override
  void initState() {
    super.initState();
    initialize();
  }

  void initialize() async {
    Response response = await get('$myIp/getEventsUser');
    setState(() {
      events = jsonDecode(response.body);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: SafeArea(
        child: SingleChildScrollView(
          child: Column(
            children: eventsList(events, context),
          ),
        ),
      ),
    );
  }
}

List<Widget> eventsList(List list, BuildContext context) {
  List<Widget> widgets = [];
  list.forEach((e) => {
        widgets.add(ReusableCard(
          colour: Colors.blueGrey,
          cardChild: Column(
            children: [
              Text(
                "${e['eventName']}",
                style: TextStyle(
                  fontSize: 30.0,
                  fontWeight: FontWeight.bold,
                ),
              ),
              Row(
                children: [
                  Expanded(
                    child: Text(
                      e['eventStartTime'].toString().substring(0, 10) +
                          "   " +
                          e['eventStartTime'].toString().substring(11),
                      style: TextStyle(
                        color: Color(0xFF24D876),
                        fontSize: 16.0,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                  Text(
                    e['eventEndTime'].toString().substring(0, 10) +
                        "   " +
                        e['eventStartTime'].toString().substring(11),
                    style: TextStyle(
                      color: Color(0xFF24D876),
                      fontSize: 16.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ],
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text(
                    e['quota'].toString(),
                    style: TextStyle(
                      fontSize: 25.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ],
              )
            ],
          ),
          onPress: () {
            if (e['quota'] != 0)
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => ApplyPage(
                      e['eventName'], e['eventStartTime'], e['eventEndTime'], e['latitude'], e['longitude']),
                ),
              );
            else
              return;
          },
        )),
      });
  return widgets;
}
