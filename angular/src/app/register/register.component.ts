import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  url;
  username = "";
  password = "";
  email = "";
  constructor(private http: HttpClient,private router:Router) { }

  regf(credentials){
    this.username = credentials["username"];
    this.password = credentials["password"];
    this.email = credentials["email"];
    
    this.register();
  }
  register(){
    console.log(this.email);
    this.url = `http://localhost:8080/login/new?username=${this.username}&password=${this.password}&email=${this.email}`
    console.log(this.url);
    this.http.get(this.url).subscribe();
    this.router.navigate(['/']);
  }


  ngOnInit(): void {
  }

}
