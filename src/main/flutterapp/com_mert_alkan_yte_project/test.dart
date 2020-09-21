import 'package:flutter/material.dart';
import 'package:http/http.dart';

void main() async {
  Response response = await get(
    'http://192.168.1.36:8081/etkinlik deneme/qrcode?name=mert&surname=alkan&tcKimlikNo=11111111110',
  );
    print(response.body);
}