import { CookieService } from '../node_modules/ngx-cookie-service'
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './user';
import { Observable } from 'rxjs';

@Injectable()
export class AuthService {
  url;
  username = "";
  password = "";
  login;
  islogged:Observable<boolean>;
  public getauth(username, password){
    
    this.username = username;
    this.password = password;
    this.url = `http://216.137.177.30:8080/login?username=${this.username}&password=${this.password}`
    let e = this.http.get(this.url);
    return e;
  

  }


  public isLogged(){
    return this.islogged;
  }



  constructor(private http: HttpClient, private cs: CookieService) {
  
   }
}
