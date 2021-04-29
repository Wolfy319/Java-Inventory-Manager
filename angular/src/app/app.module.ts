import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {RouterModule} from '@angular/router'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PanelComponent } from './panel/panel.component';
import { QuoteComponent } from './quote/quote.component';
import { NavbarComponent } from './navbar/navbar.component';
import { HomeComponent } from './home/home.component';
import { ProductListComponent } from './product-list/product-list.component';
import { LoginComponent } from './login/login.component';
import { ReccomendedComponent } from './reccomended/reccomended.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Quote } from '@angular/compiler';

import { CookieService } from 'ngx-cookie-service';
import { AuthService } from 'src/auth.service';
import { OrdersComponent } from './orders/orders.component';
import { ViewOrderComponent } from './view-order/view-order.component';
import { BuyItemsComponent } from './buy-items/buy-items.component';
import { BuyComponent } from './buy/buy.component';

@NgModule({
  declarations: [
    AppComponent,
    PanelComponent,
    LoginComponent,
    QuoteComponent,
    NavbarComponent,
    HomeComponent,
    ProductListComponent,
    ReccomendedComponent,
    OrdersComponent,
    ViewOrderComponent,
    BuyItemsComponent,
    BuyComponent
  ],
  imports: [
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,  
    RouterModule.forRoot([
      {path: 
        '',
        component: HomeComponent},
        {path: 
          'products/:page/:size',
          component: ProductListComponent},
          {path:
            'login/:username/:password',
            component: LoginComponent  },
            {path:
              'login',
              component: LoginComponent  },
            {path:
              'reccomended',
              component: ReccomendedComponent  },
              {path:
                'orders',
                component: OrdersComponent  },
                {path:
                  'orders/:id',
                  component: ViewOrderComponent  },
                  {path:
                    'buy/:item',
                    component: BuyItemsComponent  },
              
            ]),
    BrowserModule
],
  providers: [
    CookieService,
    AuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
