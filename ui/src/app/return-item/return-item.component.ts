import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-return-item',
  templateUrl: './return-item.component.html',
  styleUrls: ['./return-item.component.css']
})
export class ReturnItemComponent implements OnInit {
  returnItemForm:FormGroup;
  dueDate:any;
  fee:any;

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.returnItemForm = new FormGroup({
      'isbn': new FormControl(null, [Validators.required]),
      'readerId': new FormControl(null, Validators.required),
      'date': new FormControl(null, Validators.required)
    });
  }

  onFormSubmit() {
    let jsonFormValues = JSON.stringify(this.returnItemForm.value);
    return this.http.post('/api/returnItem', jsonFormValues).subscribe((response: any) => {
      if(response == -1){
        alert("No Record found Please Re-Enter Details");
      }else {
        console.log(response);

        this.dueDate = response.returnDate.sqlDate +" " + response.returnDate.hour + ":" +response.returnDate.minute;
        this.fee = response.fineFee;
        alert("Action successful");
      }
      this.returnItemForm.reset();

    });

  }

}
