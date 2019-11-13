import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';
import 'package:app_profilepage/dialogs.dart';

class transportPage extends StatefulWidget {
  @override
  _transportPageState createState() => new _transportPageState();
}

class _transportPageState extends State<transportPage> {

  // data to send to the server
  String _vehicle1 = '';
  String _vehicle2 = '';
  int _distance = 0;
  String _email = '';
  List<DropdownMenuItem<String>> _vehicles(){
    List<String> ddl = ["bike", "bus", "car", "motorbike", "plane", "train", "walk"];
    return ddl.map(
        (value) => new DropdownMenuItem<String>(
            child: Text(value),
            value: value,
        )
    ).toList();
  }
  Dialogs dialogs = new Dialogs();


  // result data
  double _resultCo2 = 0;
  double _resultMoney = 0;

  // Page initialization method to get the users email.
  void initState() {
    super.initState();
    FirebaseAuth.instance.currentUser().then((user) {
      setState(() {
        _email = user.email;
      });
    }).catchError((e) {
      print(e);
    });
  }


  // DEMO Function to send HTTP request and get/post data
  Future<String> getDataDemo() async {
    String url = 'https://randomuser.me/api/';
    http.Response response = await http.get(Uri.encodeFull(url), headers: {"Accept": "application/json"});

    List data;
    var extractData = json.decode(response.body);
    data = extractData["results"];

    print(data[0]["name"]["first"]);
  }

  // Function to send HTTP request and get/post data
  Future<String> getData() async{
    String url1 = 'https://gogreen82-server.herokuapp.com/api/transport/';
    String url= url1 + _email +"/"+ _vehicle1 +"/"+ _vehicle2 +"/"+ _distance.toString();

    http.Response response = await http.get(Uri.encodeFull(url), headers: {"Accept" : "Application/json"});
    print(response.body);

    var extractData = json.decode(response.body);
    _resultCo2 = extractData["resultCo2"];
    _resultMoney = extractData["resultMoney"];
    print(_resultCo2);
    print(_resultMoney);

    dialogs.information(context, "test", _resultCo2, _resultMoney);
  }



  @override
  Widget build(BuildContext context) {
    return Scaffold(
      floatingActionButton: FloatingActionButton.extended(
          onPressed: (){
            getData();
//            dialogs.information(context, "test", _resultCo2, _resultMoney);
          },
          icon: Icon(Icons.done),
          label: Text('Save')),
      appBar: AppBar(
          centerTitle: true,
          title: Text('Enter Transport details')),
      body: Container(
        padding: EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Center(
              child: DropdownButton(
                value: _vehicle1 == "" ? null : _vehicle1,
                items: _vehicles(),
                onChanged: (String value) {
                  _vehicle1 = value;
                  setState(() {
                  });
                },
                hint: Text("Vehicle that you usually take?"),
              ),
            ),

            Center(
              child: DropdownButton(
                value: _vehicle2 == "" ? null : _vehicle2,
                items: _vehicles(),
                onChanged: (String value) {
                  _vehicle2 = value;
                  setState(() {
                  });
                },
                hint: Text("Vehicle that you took instead?",
                style: new TextStyle(
//
                ),),
              ),
            ),



            //--------------------------------------

//            ListTile(
//              title: TextField(
//                onChanged: (value) {
//                  _vehicle1 = value;
//                },
//                decoration: InputDecoration(
//                    icon: Icon(Icons.fastfood),
//                    labelText: 'Vehicle that you usually take?',
//                    labelStyle: TextStyle(fontWeight: FontWeight.bold)),
//                maxLines: 1,
//              ),
//            ),


//            TextField(
//              onChanged: (value) {
//                _vehicle2 = value;
//              },
//              decoration: InputDecoration(
//                icon: Icon(Icons.local_dining),
//                labelText: 'Vehicle that you took instead?',
//                labelStyle: TextStyle(fontWeight: FontWeight.bold),
//              ),
//            ),
            TextField(
              keyboardType: TextInputType.number,
              onChanged: (value) {
                _distance = int.parse(value);
              },
              decoration: InputDecoration(
                  icon: Icon(Icons.euro_symbol),
                  labelText: 'Distance travelled in KM?',
                  labelStyle: TextStyle(fontWeight: FontWeight.bold)),
            )
          ],
        ),
      ),
    );
  }
}
