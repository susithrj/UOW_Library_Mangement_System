import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-veiwlist',
  templateUrl: './veiwlist.component.html',
  styleUrls: ['./veiwlist.component.css']
})
export class VeiwlistComponent implements OnInit {
  bookData: any;//variable to hold table data
  searchForm: FormGroup;

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    //getting data from backend using http post request
    this.searchForm = new FormGroup({
      'title': new FormControl(null, [Validators.required]),

    });
    this.getData();

  }
  getData(){
    return this.http.post('/api/getBookData', {}).subscribe((response: any) => {
      this.bookData = response;

    });
  }

  onFormSubmit() {
    let jsonFormValues = JSON.stringify(this.searchForm.value);
    return this.http.post('/api/search', jsonFormValues).subscribe((response: any) => {
      this.bookData = response;
    });


  }
}
