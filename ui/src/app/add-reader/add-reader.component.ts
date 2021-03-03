import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-add-reader',
  templateUrl: './add-reader.component.html',
  styleUrls: ['./add-reader.component.css']
})
export class AddReaderComponent implements OnInit {
  readerForm: FormGroup;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    //creating the form object
    this.readerForm = new FormGroup({
      //validators are used in the form
      'name': new FormControl(null, Validators.required),
      'email': new FormControl(null, [Validators.required, Validators.email]),
      //email validation is used
      'number': new FormControl(null, Validators.required),

    });
  }

  onFormSubmit() {
    //making the form values in json format
    let jsonFormValues = JSON.stringify(this.readerForm.value);

    //sending data for backend and getting id=f there is a response from backend
    //using http post request
    return this.http.post('/api/addReader', jsonFormValues).subscribe((response: any) => {

      //alerting the user about the progress
      if (response === 1) {
        alert("User " + this.readerForm.value.name + " is successfully added");
      } else if (response == 0) {
        alert("ERROR - A reader exists for this ISBN");

      } else {
        alert("Sorry Database Error");

      }
      this.readerForm.reset();
    });


  }

}
