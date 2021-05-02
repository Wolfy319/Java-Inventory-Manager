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
  constructor(private route: ActivatedRoute) { 
    this.ngOnInit();

  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(res=>{
      this.item = res.get("item")
      
    })
  }

}
