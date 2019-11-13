import 'package:flutter/material.dart';

class DashboardPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(),
        body: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Container(
              child: Stack(
                children: <Widget>[
                  Container(
                    padding: EdgeInsets.fromLTRB(15.0, 60, 0.0, 0.0),
                    child: Text('Ready',
                        style: TextStyle(
                          fontFamily: 'Quicksand',
                            fontSize: 80.0,)),
                  ),
                  Container(
                    padding: EdgeInsets.fromLTRB(16.0, 175.0, 0.0, 0.0),
                    child: Text('To',
                        style: TextStyle(
//                          fontFamily: 'Rubik',
                            fontSize: 80.0,)),
                  ),
                  Container(
                    padding: EdgeInsets.fromLTRB(16.0, 285.0, 0.0, 0.0),
                    child: Text('GoGreen',
                        style: TextStyle(
                            fontFamily: 'Quicksand',
                            fontSize: 80.0, fontWeight: FontWeight.bold)),
                  ),
                  Container(
                    padding: EdgeInsets.fromLTRB(330, 285.0, 0.0, 0.0),
                    child: Text('?',
                        style: TextStyle(
                            fontSize: 80.0,
                            fontWeight: FontWeight.bold,
                            color: Colors.teal)),
                  ),
//                  Container(
//                    padding: EdgeInsets.fromLTRB(50, 510.0, 0.0, 0.0),
//                    child: Text('Click below to enter your activities',
//                        style: TextStyle(
//                            fontSize: 20.0,
//                            fontWeight: FontWeight.normal,
//                            color: Colors.black87)),
//                  )
                ],
              ),
            ),
          ],
        ));
  }
}
