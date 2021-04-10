import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import jwt_decode from 'jwt-decode';
@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  orders;
  data;
  email = "";
  page = 1;
  size = 10;
  url = ""

  constructor(public cookies:CookieService, private http:HttpClient, private route: ActivatedRoute) { 
    this.ngOnInit();
    if(cookies.get("session")){
      this.data = cookies.get("session");
      this.data = jwt_decode(this.data);
      console.log(this.data);
      this.email = this.data["email"];
      console.log(this.email);
    }
    this.url = `http://localhost:8080/orders?id=${this.email}`; 
    console.log(this.url)
    this.http.get(this.url).subscribe(res =>{
      this.orders = res;
      console.log(this.orders);
    })
  }
  ngOnInit(): void {
    }
}
