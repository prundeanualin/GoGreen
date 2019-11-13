import 'package:flutter/material.dart';

class Dialogs{
  information(BuildContext context, String title, double co2Saved, double moneySaved){
    return showDialog(
      context: context,
      barrierDismissible: true,
      builder: (BuildContext context){
        return AlertDialog(
          title: Text("Congratulations"),
          content: SingleChildScrollView(
            child: ListBody(
              children: <Widget>[
                Row(
                  children: <Widget>[
                    Text("Co2 Saved : " + co2Saved.toString()),
                  ],
                ),
                Row(
                  children: <Widget>[
                    Text("Money saved : " + moneySaved.toString()),
                  ],
                ),
              ],
            ),
          ),
          actions: <Widget>[
            FlatButton(
              onPressed: () => Navigator.pop(context),
              child: Text('OK'),
            )
          ],
        );
      }
    );
  }
}