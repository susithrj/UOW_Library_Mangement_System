import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-deleteitem',
  templateUrl: './deleteitem.component.html',
  styleUrls: ['./deleteitem.component.css']
})
export class DeleteitemComponent implements OnInit {
  deleteItemForm: FormGroup;
  bookSlots:any;//variable to hold number of slots
  dvdSlots:any;//variable to hold number of slots

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.getSlots();

    //creating the form object
    this.deleteItemForm = new FormGroup({
    'isbn': new FormControl(null, [Validators.required]),
  });
  }

  onFormSubmit() {
    //making the form values in json format
    let jsonFormValues = JSON.stringify(this.deleteItemForm.value);

    //sending data for backend and getting if there is a response from backend
    //using http post request
    return this.http.post('/api/deleteItem', jsonFormValues).subscribe((response: any) => {
      if(response){
        //alerting the user about the progress
        alert("Item Deleted Successfully");

      }else {
        alert("Item Not Found");

      }
      this.getSlots();
      this.deleteItemForm.reset();


    });


  }
  getSlots() {
    //getting number of items through http post request
    return this.http.post('/api/getSlots', {}).subscribe((response: any) => {
      this.dvdSlots = response[0];
      this.bookSlots = response[1];


    });
  }

}
