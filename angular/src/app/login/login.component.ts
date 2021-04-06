import { CookieService } from 'ngx-cookie-service';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { BrowserModule } from '@angular/platform-browser'
import { setClassMetadata } from '@angular/core/src/r3_symbols';
import { AuthService } from 'src/auth.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    username;
    password;
    url;
    data;
    get;
    
  login: Observable<Object>;

  // try callback for null error

    constructor( private route:ActivatedRoute, private router:Router,private service:AuthService,private cookieservice:CookieService) { 
      this.ngOnInit();
      if(this.get){
     this.authenticate();
    }
    }

    authenticate(){
      this.login = this.service.getauth(this.username, this.password);
      this.login.subscribe((r) => {
        this.data= r;
        console.log(r);
         this.cookieservice.set("session", r["value"]);
        if(this.data != null)

        this.router.navigate(['/']);
        else  
          console.log("invalid");
      });
       

    }

    loginf(credentials){
      this.username = credentials["username"];
      this.password = credentials["password"];
      this.authenticate();
    }

    ngOnInit(): void {
     this.route.queryParamMap.subscribe( res=>{
        this.password = res.get("password")
        this.username = res.get("username")
     });
     if(this.password != null){
       this.get = true;
     }
     else
      this.get=false;
    }
  }
