import 'dart:async';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class historyPage extends StatefulWidget {
  final String userEmail;

  const historyPage(this.userEmail, {Key key}): super(key: key);

  @override
  _historyPageState createState() => new _historyPageState();
}

class _historyPageState extends State<historyPage> {
  // class fields
  List data;

  // Page initialization method to get the users email.
  @override
  void initState() {
    super.initState();
    getUserHistory();
  }

  // Function to get UserHistory data
  Future<String> getUserHistory() async {
    String url = 'https://gogreen82-server.herokuapp.com/api/history/' + widget.userEmail;
    print(url);

    http.Response response = await http.get(Uri.encodeFull(url), headers: {"Accept" : "Application/json"});
    print(response.body);

    this.setState(() {
       data = json.decode(response.body.toString());
    });
  }


  @override
  Widget build(BuildContext context) {
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
                title: Text(data[index]["date"].toString(), style: TextStyle(color: Colors.black87, fontWeight: FontWeight.bold)),
                subtitle: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text("Co2 saved : " + data[index]["co2Saved"].toString(),
                    style: TextStyle(color: Colors.black87),),
                    Text("Money saved : "+ data[index]["moneySaved"].toString(),
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































//  // Function to get the history data from server
//  Future<List<history>> _getHistory() async {
//    String url1 = 'https://gogreen82-server.herokuapp.com/api/history/';
//    String url = url1 + _email;
//
//    http.Response data = await http
//        .get(Uri.encodeFull(url), headers: {"Accept": "Application/json"});
//
//    print(data.body);
//    var jsonData = json.decode(data.body);
//
//    List<history> userHistory = [];
//    for (var u in jsonData) {
//      history history1 = history(u["userEmail"], u["activity"], u["co2Saved"],
//          u["moneySaved"], u["date"]);
//      userHistory.add(history1);
//    }
//
//    print(userHistory.length);
//    return userHistory;
//  }

//  @override
//  Widget build(BuildContext context) {
//    return new Scaffold(
//      appBar: new AppBar(
//        title: new Text("Your green activity history"),
//      ),
//      body: Container(
//        child: FutureBuilder(
//          future: _getHistory(),
//          builder: (BuildContext context, AsyncSnapshot snapshot) {
//            if (snapshot.data == null) {
//              return Container(
//                child: Center(
//                  child: Text("Loading data..."),
//                ),
//              );
//            } else {
//              return ListView.builder(
//                itemCount: snapshot.data.length,
//                itemBuilder: (BuildContext context, int index) {
//                  return ListTile(
//                    title: Text(snapshot.data[index].date),
//                  );
//                },
//              );
//            }
//          },
//        ),
//      ),
//    );
//  }
}

class history {
  String userEmail;
  String activity;
  double co2Saved;
  double moneySaved;
  DateTime date;

  history(
      this.userEmail, this.activity, this.co2Saved, this.moneySaved, this.date);
}
