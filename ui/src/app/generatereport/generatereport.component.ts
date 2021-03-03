import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-generatereport',
  templateUrl: './generatereport.component.html',
  styleUrls: ['./generatereport.component.css']
})
export class GeneratereportComponent implements OnInit {

  reportData:any; //variable to hold table data

  constructor(private http: HttpClient) { }

  ngOnInit() {
    //getting data from backend using http post request
    return this.http.post('/api/getReport', {}).subscribe((response: any) => {
      this.reportData = response;

    });


  }

}
