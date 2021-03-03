import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-addbook',
  templateUrl: './addbook.component.html',
  styleUrls: ['./addbook.component.css']
})
export class AddbookComponent implements OnInit {
  addBookForm: FormGroup;
  slots: any;//variable to hold number of slots

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.getSlots();

    //creating the form object
    this.addBookForm = new FormGroup({
      //validators are used in the form
      'title': new FormControl(null, Validators.required),
      'date': new FormControl(null, Validators.required),
      'sector': new FormControl(null, Validators.required),
      'isbn': new FormControl(null, Validators.required),
      'pages': new FormControl(null, Validators.required),
      'authors': new FormControl(null, Validators.required)
    });
  }

  onFormSubmit() {

    //making the form values in json format
    let jsonFormValues = JSON.stringify(this.addBookForm.value);

    //sending data for backend and getting id=f there is a response from backend
    //using http post request
    return this.http.post('/api/bookSubmit', jsonFormValues).subscribe((response: any) => {

      //alerting the user about the progress
      if (response === 1) {
        alert("The book " + this.addBookForm.value.title + " is successfully added");
      } else if (response == 0) {
        alert("ERROR - A book exists for this ISBN");

      } else if (response == -1) {
        alert("Sorry Database Error");

      } else {
        alert("Running out Of Storage");
      }
      this.getSlots();
      this.addBookForm.reset();


    });


  }

  getSlots() {
    //getting number of items through http post request
    return this.http.post('/api/getSlots', {}).subscribe((response: any) => {
      this.slots = response[1];//initializing the slot variable
    });
  }
}
