import 'dart:async';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class careerpageworking extends StatefulWidget {
  final String userEmail;

  const careerpageworking(this.userEmail, {Key key}) : super(key: key);

  @override
  _careerPageWorkingState createState() => new _careerPageWorkingState();
}

class _careerPageWorkingState extends State<careerpageworking>{

  List<innerClass> data = new List();

  @override
  void initState() {
    super.initState();
    getUserCareer();
  }

  // Function to get UserHistory data
  Future<String> getUserCareer() async {
    String url = 'https://gogreen82-server.herokuapp.com/api/usercareer/' + widget.userEmail;
    print(url);

    http.Response response = await http.get(Uri.encodeFull(url), headers: {"Accept" : "Application/json"});
    print(response.body);

    this.setState(() {
      var exractedData = json.decode(response.body.toString());
      data.add(new innerClass("User Email", exractedData[0]["userEmail"]));
      data.add(new innerClass("Total co2 saved", exractedData[0]["totalCo2Saved"].toString()));
      data.add(new innerClass("Total money saved", exractedData[0]["totalMoneySaved"].toString()));
      data.add(new innerClass("Total Energy saved", exractedData[0]["totalEnergySaved"].toString()));
      data.add(new innerClass("CO2 saved by food", exractedData[0]["foodCo2Saved"].toString()));
      data.add(new innerClass("CO2 saved by transport", exractedData[0]["transportCo2Saved"].toString()));
      data.add(new innerClass("CO2 saved by solarpanels", exractedData[0]["solarCo2Saved"].toString()));
      data.add(new innerClass("CO2 saved by lowering temperature", exractedData[0]["tempCo2Saved"].toString()));
      data.add(new innerClass("Money saved by food", exractedData[0]["foodMoneySaved"].toString()));
      data.add(new innerClass("Money saved by transport", exractedData[0]["transportMoneySaved"].toString()));
      data.add(new innerClass("Money saved by solarpanels", exractedData[0]["solarMoneySaved"].toString()));
      data.add(new innerClass("Money saved by lowering temperature", exractedData[0]["tempMoneySaved"].toString()));
    });
    print(data);
  }





  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
        appBar: AppBar(
          title: Text("Your green activity history"),
        ),
        body: ListView.builder(
          itemCount: data == null ? 0 : data.length,
          itemBuilder: (BuildContext context, int index){
            return Card(
              elevation: 8.0,
              margin: new EdgeInsets.symmetric(horizontal: 10.0, vertical: 6.0),
              child: Container(
                decoration: BoxDecoration(color: Colors.greenAccent),
                child: ListTile(
                  contentPadding: EdgeInsets.symmetric(horizontal: 20.0, vertical: 10.0),
                  leading: Container(
                    padding: EdgeInsets.only(right: 12.0),
                    decoration: new BoxDecoration(
                        border: new Border(
                            right: new BorderSide(width: 1.0, color: Colors.black87)
                        )
                    ),
                    child: Icon(Icons.fastfood, color: Colors.black87,),
                  ),
                  title: Text(data[index].recordName, style: TextStyle(color: Colors.black87, fontWeight: FontWeight.bold)),
                  subtitle: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(data[index].recordValue,
                        style: TextStyle(color: Colors.black87),)
                    ],
                  ),
                  trailing: Icon(Icons.keyboard_arrow_right, color: Colors.black87, size: 30.0),
                ),
              ),
            );
          },
        )
    );
  }

}

class innerClass {
  String recordName;
  String recordValue;

  innerClass(this.recordName, this.recordValue);
}