import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-lend-item',
  templateUrl: './lend-item.component.html',
  styleUrls: ['./lend-item.component.css']
})
export class LendItemComponent implements OnInit {
  lendItemForm:FormGroup;
  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    //creating the form object
    this.lendItemForm = new FormGroup({


      'isbn': new FormControl(null, [Validators.required]),
      'readerId': new FormControl(null, Validators.required),
      'date': new FormControl(null, Validators.required)
    });
  }

  onFormSubmit() {
    //making the form values in json format
    let jsonFormValues = JSON.stringify(this.lendItemForm.value);


    //sending data for backend and getting if there is a response from backend
    //using http post request
    return this.http.post('/api/lendItem', jsonFormValues).subscribe((response: any) => {
      //alerting the user about the progress
      if(response === 1){

        alert("Action Successful");

      }else if(response == 0){
        alert("Reader not Found");


      }else if(response == -1){
        alert("Book not found");

      }else {
        alert("Book Already Taken Please make an Reservation");
      }
      this.lendItemForm.reset();

    });

  }
}
