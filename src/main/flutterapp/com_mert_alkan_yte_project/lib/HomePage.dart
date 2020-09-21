import 'package:com_mert_alkan_yte_project/EventsList.dart';
import 'package:com_mert_alkan_yte_project/ListMyEvents.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Center(
            child: Text(
              'Welcome to the events app!',
              textAlign: TextAlign.center,
              style: TextStyle(
                fontSize: 18.0,
                color: Color(0xFF8D8E98),
              ),
            ),
          ),
          SizedBox(
            width: double.infinity,
            height: 30,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              FlatButton(
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => EventsList(title: 'List of events'),
                    ),
                  );
                },
                child: Row(
                  children: [
                    Icon(Icons.add),
                    Text('    Apply for an event'),
                  ],
                ),
              ),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              FlatButton(
                onPressed: () {Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => ListMyEvents(title: 'List of events'),
                  ),
                );},
                child: Row(
                  children: [
                    Icon(Icons.account_circle),
                    Text('    Applied events'),
                  ],
                ),
              ),
            ],
          )
        ],
      ),
    );
  }
}
