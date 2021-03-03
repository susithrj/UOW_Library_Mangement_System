import { BrowserModule } from '@angular/platform-browser';
import { NgModule , OnInit } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HTTP_INTERCEPTORS, HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';
import { AppComponent } from './app.component';
import { RouteExampleComponent } from './welcome-page/welcome';
//import { AppRoutingModule } from './app-routing.module';

import { AppHttpInterceptorService } from './http-interceptor.service';
import { AddbookComponent } from './addbook/addbook.component';
import { AdddvdComponent } from './adddvd/adddvd.component';
import { LendItemComponent } from './lend-item/lend-item.component';
import { ReturnItemComponent } from './return-item/return-item.component';
import { DeleteitemComponent } from './deleteitem/deleteitem.component';
import { VeiwlistComponent } from './veiwlist/veiwlist.component';
import { GeneratereportComponent } from './generatereport/generatereport.component';
import { AddReaderComponent } from './add-reader/add-reader.component';
import { VeiwReadersComponent } from './veiw-readers/veiw-readers.component';
import { MakeReservationComponent } from './make-reservation/make-reservation.component';
import { VeiwResevationsComponent } from './veiw-resevations/veiw-resevations.component';
const routes: Routes = [
  {
    path: 'deleteitem',
    component: DeleteitemComponent,

  },
  {
    path: 'veiwlist',
    component: VeiwlistComponent,

  },
  {
    path: 'addbook',
    component: AddbookComponent,

  },
  {
    path: 'adddvd',
    component: AdddvdComponent,

  },
  {
    path: 'returnitem',
    component: ReturnItemComponent,

  },
  {
    path: 'lenditem',
    component: LendItemComponent,

  },
  {
    path: 'veiwReader',
    component: VeiwReadersComponent,

  },
  {
    path: 'veiwRes',
    component: VeiwResevationsComponent,

  },
  {
    path: 'makeRes',
    component: MakeReservationComponent,

  },
  {
    path: 'addReader',
    component: AddReaderComponent,

  },
  {
    path: 'report',
    component: GeneratereportComponent,

  },
  {
    path: 'login',
    component: RouteExampleComponent,

  },

  {
    path: '**',
    redirectTo: '/login',
    pathMatch: 'full'
  },

];

@NgModule({
  declarations: [
    AppComponent,
    RouteExampleComponent,
    AddbookComponent,
    AdddvdComponent,
    LendItemComponent,
    ReturnItemComponent,
    DeleteitemComponent,
    VeiwlistComponent,
    GeneratereportComponent,
    AddReaderComponent,
    VeiwReadersComponent,
    MakeReservationComponent,
    VeiwResevationsComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    HttpClientXsrfModule.withOptions({
      cookieName: 'Csrf-Token',
      headerName: 'Csrf-Token',
    }),
    RouterModule.forRoot(routes)
  ],
  providers: [


    {
      multi: true,
      provide: HTTP_INTERCEPTORS,
      useClass: AppHttpInterceptorService
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
