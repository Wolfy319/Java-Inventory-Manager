import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms'
import { Location } from '@angular/common';
@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  entries;
  page:number;
  size:number;
  url;

  numEntries: any = [5, 10, 20, 50, 100, 1000, 10000];
  form = new FormGroup({
    website: new FormControl('', Validators.required)
  });
   
  get f(){
    return this.form.controls;
  }
   
  next(){
    this.page +=1
    this.location.replaceState(`/products/${this.page}/${this.size}`);
    this.url = `http://localhost:8080/products?page=${this.page}&size=${this.size}` 
    console.log(this.url)
    this.http.get(this.url).subscribe(res =>{
      this.entries = res;
      console.log(this.entries);
    })
  }

  submit(){
    this.page = 1
    this.size = this.form.value['website']
    this.location.replaceState(`/products/${this.page}/${this.size}`);
    this.url = `http://localhost:8080/products?page=${this.page}&size=${this.size}` 
    console.log(this.url)
    this.http.get(this.url).subscribe(res =>{
      this.entries = res;
      console.log(this.entries);
    })
  }

  constructor(private http:HttpClient, private route: ActivatedRoute, private location:Location) { 
    this.ngOnInit();
    this.url = `http://localhost:8080/products?page=${this.page}&size=${this.size}` 
    console.log(this.url)
    this.http.get(this.url).subscribe(res =>{
      this.entries = res;
      console.log(this.entries);
    })
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(res=>{
      this.page = +res.get("page")
      this.size = +res.get("size")
    })
  }

}
