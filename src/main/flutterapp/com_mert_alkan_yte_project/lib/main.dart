
import 'package:com_mert_alkan_yte_project/HomePage.dart';
import 'package:flutter/material.dart';
import 'package:com_mert_alkan_yte_project/EventsList.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData.dark().copyWith(
        primaryColor: Color(0xFF0A0E21),
        scaffoldBackgroundColor: Color(0xFF0A0E21),
      ),
     // home: EventsList(title: 'Events List'),
      home: HomePage(),
    );
  }
}

