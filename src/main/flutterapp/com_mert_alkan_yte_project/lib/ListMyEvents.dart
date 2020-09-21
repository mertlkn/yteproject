import 'package:flutter/material.dart';
import 'package:com_mert_alkan_yte_project/ApplyPage.dart';
import 'package:com_mert_alkan_yte_project/DataBaseHelper.dart';
import 'package:com_mert_alkan_yte_project/ReusableCard.dart';

class ListMyEvents extends StatefulWidget {
  ListMyEvents({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _ListMyEventsState createState() => _ListMyEventsState();
}

class _ListMyEventsState extends State<ListMyEvents> {
  List<Events> events = [];
  List<Widget> eventWidgets = [];

  @override
  void initState() {
    super.initState();
    initialize();
  }

  Future<List<Widget>> initialize() async {
    DatabaseHelper helper = DatabaseHelper.instance;
    List<Events> event = await helper.queryAllEvents();
    setState(() {
      events = event;
      eventWidgets = eventsList(events, context);
    });
    return eventWidgets;
  }

  List<Widget> eventsList(List<Events> list, BuildContext context) {
    List<Widget> widgets = [];
    list.forEach((e) => {
          widgets.add(ReusableCard(
            colour: Colors.blueGrey,
            cardChild: Column(
              children: [
                Text(
                  "${e.eventName}",
                  style: TextStyle(
                    fontSize: 30.0,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                Row(
                  children: [
                    Expanded(
                      child: Text(
                        e.eventStartTime.toString().substring(0, 10) +
                            "   " +
                            e.eventStartTime.toString().substring(11),
                        style: TextStyle(
                          color: Color(0xFF24D876),
                          fontSize: 16.0,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                    Text(
                      e.eventEndTime.toString().substring(0, 10) +
                          "   " +
                          e.eventStartTime.toString().substring(11),
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
                    Expanded(
                      child: Text(
                        e.name.toString(),
                        style: TextStyle(
                          fontSize: 25.0,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                    Expanded(
                      child: Text(
                        e.surname.toString(),
                        style: TextStyle(
                          fontSize: 25.0,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                    Text(
                      e.tcKimlikNo.toString(),
                      style: TextStyle(
                        fontSize: 25.0,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ],
                ),
                Row(
                  children: [
                    Expanded(
                      child: SizedBox(),
                    ),
                    FlatButton(
                      color: Colors.red,
                      onPressed: () {
                        _delete(e.eventName, e.tcKimlikNo);
                        initialize();
                      },
                      child: Text('delete'),
                    )
                  ],
                )
              ],
            ),
            onPress: () {},
          )),
        });
    return widgets;
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

_delete(String eventName, String tcKimlikNo) {
  DatabaseHelper helper = DatabaseHelper.instance;
  helper.delete(eventName, tcKimlikNo);
}
