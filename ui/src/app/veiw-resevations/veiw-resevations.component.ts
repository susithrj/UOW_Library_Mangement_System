import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
@Component({
  selector: 'app-veiw-resevations',
  templateUrl: './veiw-resevations.component.html',
  styleUrls: ['./veiw-resevations.component.css']
})
export class VeiwResevationsComponent implements OnInit {
  resData:any; //variable to hold reservation table data
  constructor(private http: HttpClient) { }

  ngOnInit() {
   //getting data from backend using http post request
    return this.http.post('/api/viewReservations', {}).subscribe((response: any) => {
      this.resData = response;


    });

  }


}
