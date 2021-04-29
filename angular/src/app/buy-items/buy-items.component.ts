import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { CookieService } from 'ngx-cookie-service';
import jwt_decode from 'jwt-decode';
@Component({
  selector: 'app-buy-items',
  templateUrl: './buy-items.component.html',
  styleUrls: ['./buy-items.component.css']
})
export class BuyItemsComponent implements OnInit {
  @ViewChild('location', { static: true }) loc: ElementRef;
  item = "";
  url: string;
  entries: Object;
  data$: any;
  dt$: any;
 
  constructor(private http:HttpClient, private router:Router, private route: ActivatedRoute, private location:Location, public cookies:CookieService,) 
  { this.ngOnInit(); }


  
  
  yes() {
    if(this.cookies.get("session")){
      this.data$ = this.cookies.get("session");
      this.data$ = jwt_decode(this.data$);
      console.log(this.data$);
      this.dt$ = this.data$["email"];
    this.router.navigate([`/orders`]);
     this.url = `http://localhost:8080/buy?id=${this.item}&email=${this.dt$}&loc=${this.loc}` 
     console.log(this.url)
     this.http.get(this.url).subscribe(res =>{
       this.entries = res;
       console.log(this.entries);
     })
  }
  else{
    this.router.navigate([`/`]);
  }
}

  no() {
    this.router.navigate([`/`]);
  }


  ngOnInit(): void {
    this.route.paramMap.subscribe(res=>{
      this.item = res.get("item")
      
      
    })
  }
}
