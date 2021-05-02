import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-view-order',
  templateUrl: './view-order.component.html',
  styleUrls: ['./view-order.component.css']
})
export class ViewOrderComponent implements OnInit {
  productID:any;
  date:any;
  quantity:any;
  email:any;
  address:any;
  item: string;
  url: string;
  entry;
  constructor(private http:HttpClient, private route: ActivatedRoute) { 
    this.ngOnInit();
    this.url = `http://localhost:8080/order?id=${this.item}` 
    console.log(this.url)
    this.http.get(this.url).subscribe(res =>{
      this.entry = res;
      console.log(this.entry);
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(res=>{
      this.item = res.get("id")
      
    })
  }

}
