import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-buy-items',
  templateUrl: './buy-items.component.html',
  styleUrls: ['./buy-items.component.css']
})
export class BuyItemsComponent implements OnInit {
  item = "";
  constructor(private route: ActivatedRoute) 
  { this.ngOnInit(); }

  
  yes() {
    console.log("clicked yes")
  }

  no() {
    console.log("clicked no")
  }


  ngOnInit(): void {
    this.route.paramMap.subscribe(res=>{
      this.item = res.get("item")
      
    })
  }
}
