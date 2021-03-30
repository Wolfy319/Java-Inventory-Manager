import { Component, Injectable, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import jwt_decode from 'jwt-decode';
import { Observable } from 'rxjs';
import { AuthService } from 'src/auth.service';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})


export class NavbarComponent implements OnInit {
  data$: string ;
  dt$ :Observable<string>;
  name$ :any;
  constructor(public cookies:CookieService, public auth:AuthService) {

    if(cookies.get("session")){
      this.data$ = cookies.get("session");
      this.data$ = jwt_decode(this.data$);
      this.dt$ = this.data$["Username"];
      

    }
    else this.data$ = "0";
    
  }
   
  public logout(){
    console.log("delete")
    this.cookies.delete("session");
  }
  ngOnInit(): void {
    this.loggedIn$ = this.auth.isLogged();
  }


  loggedIn$: Observable<boolean>;
  username$: Observable<String>;

}
