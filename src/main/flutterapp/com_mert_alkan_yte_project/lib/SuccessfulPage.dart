import 'dart:convert';

import 'package:com_mert_alkan_yte_project/ReusableCard.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart';
import 'package:com_mert_alkan_yte_project/MyIp.dart';

class SuccessfulPage extends StatelessWidget {
  final String eventName, name, surname, tcKimlikNo;

  SuccessfulPage(this.eventName, this.name, this.surname, this.tcKimlikNo);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: ReusableCard(
        colour: Colors.blueGrey,
        cardChild: Column(
          children: [
            Expanded(
              child: Image.network(
                '$myIp/$eventName/qrcode?name=$name&surname=$surname&tcKimlikNo=$tcKimlikNo',
              ),
            ),
            Expanded(
              child: Column(
                children: [
                  Text(
                    'Successfully applied!',
                    style: TextStyle(
                      color: Color(0xFF24D876),
                      fontSize: 22.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  Text(
                    'Here is your QR Code!',
                    style: TextStyle(
                      color: Colors.black,
                      fontSize: 22.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  Text(
                    'An email has been sent to your account including your info!',
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      color: Colors.amber,
                      fontSize: 22.0,
                      fontWeight: FontWeight.bold,

                    ),
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      FlatButton(
                        child: Text('Go to home page'),
                        onPressed: () {
                          Navigator.pop(context);
                          Navigator.pop(context);
                          Navigator.pop(context);
                        },
                      ),
                    ],
                  )
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}
