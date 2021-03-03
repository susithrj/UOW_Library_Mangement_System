import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-veiw-readers',
  templateUrl: './veiw-readers.component.html',
  styleUrls: ['./veiw-readers.component.css']
})
export class VeiwReadersComponent implements OnInit {
  readerData:any; //variable to hold reader table data
  constructor(private http: HttpClient) { }

  ngOnInit() {
    //getting data from backend using http post request
    return this.http.post('/api/viewReaders', {}).subscribe((response: any) => {
      this.readerData = response;


    });

  }

}
