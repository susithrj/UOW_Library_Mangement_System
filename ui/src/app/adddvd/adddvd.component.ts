import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-adddvd',
  templateUrl: './adddvd.component.html',
  styleUrls: ['./adddvd.component.css']
})
export class AdddvdComponent implements OnInit {
  DVDForm: FormGroup;
  slots: any;//variable to hold number of slots

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    //creating the form object
    this.DVDForm = new FormGroup({
      'title': new FormControl(null, Validators.required),
      'date': new FormControl(null, Validators.required),
      'sector': new FormControl(null, Validators.required),
      'isbn': new FormControl(null, Validators.required),
      'producer': new FormControl(null, Validators.required),
      'duration': new FormControl(null, Validators.required),
      'actors': new FormControl(null, Validators.required),
      'languages': new FormControl(null, Validators.required),
      'subtitles': new FormControl(null, Validators.required)
    });
    this.getSlots();
  }

  onFormSubmit() {
    //making the form values in json format
    let jsonFormValues = JSON.stringify(this.DVDForm.value);

    //sending data for backend and getting if there is a response from backend
    //using http post request
    return this.http.post('/api/dvdSubmit', jsonFormValues).subscribe((response: any) => {

      //alerting the user about the progress
      if (response === 1) {
        alert("The DVD " + this.DVDForm.value.title + " is successfully added");
      } else if (response == 0) {
        alert("ERROR - A book exists for this ISBN");

      } else if (response == -1) {
        alert("Sorry Database Error");

      } else {
        alert("Running out Of Storage");
      }
      this.getSlots();
      this.DVDForm.reset();

    });

  }

  getSlots() {
    //getting number of items through http post request
    return this.http.post('/api/getSlots', {}).subscribe((response: any) => {
      console.log(response);
      this.slots = response[0];//initializing the slot variable

    });
  }
}
