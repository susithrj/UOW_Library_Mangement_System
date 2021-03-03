import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
@Component({
  selector: 'app-make-reservation',
  templateUrl: './make-reservation.component.html',
  styleUrls: ['./make-reservation.component.css']
})
export class MakeReservationComponent implements OnInit {
  collectionDate:any;
  reserveForm:FormGroup;
  constructor(private http: HttpClient) {
  }
  ngOnInit() {


    //creating the form object
    this.reserveForm = new FormGroup({
      'isbn': new FormControl(null, [Validators.required]),
      'readerId': new FormControl(null, Validators.required),

    });
  }
  onFormSubmit() {

    //making the form values in json format
    let jsonFormValues = JSON.stringify(this.reserveForm.value);

    //sending data for backend and getting if there is a response from backend
    //using http post request
    return this.http.post('/api/resItem', jsonFormValues).subscribe((response: any) => {
    //alerting the user about the progress
      if(response === -2){
        alert("You have already done a reservation");

      }else if(response === 0){
        alert("Item is not Taken from a Reader");


      }else if(response === -1){
        alert("Cannot Make a Reservation since Current Reader have't returned to expected date");

      }else {
        alert("Action Successful");
      }
      this.collectionDate = response.collectionDate.sqlDate;

      this.reserveForm.reset();

    });

  }

}
