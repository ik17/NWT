import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { RouterModule } from '@angular/router';
import { APP_BASE_HREF } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { ArticleReviewComponent } from './article-review/article-review.component';
import { ReviewWrapperComponent } from './review-wrapper/review-wrapper.component';
import { ReviewElementComponent } from './review-wrapper/review-element/review-element.component';
import { UsersListComponent } from './users-list/users-list.component';
import { ArticleSearchComponent } from './article-search/article-search.component';
import { ReviewerArticlesComponent } from './reviewer-articles/reviewer-articles.component';
import { RegisterComponent } from './register/register.component';
import { UserViewComponent } from './user-view/user-view.component';
import { HomeComponent } from './home/home.component';
import { RouteGuardService } from './_services/route-guard.service';
import { AngularFireModule } from 'angularfire2';
import { AngularFireStorageModule } from 'angularfire2/storage';
import { SubmitClanakComponent } from './submit-clanak/submit-clanak.component';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    ArticleReviewComponent,
    ReviewWrapperComponent,
    ReviewElementComponent,
    UsersListComponent,
    ArticleSearchComponent,
    ReviewerArticlesComponent,
    RegisterComponent,
    UserViewComponent,
    HomeComponent,
    SubmitClanakComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule, 
    NgbModule, 
    HttpClientModule,
    ReactiveFormsModule,
	NgMultiSelectDropDownModule.forRoot(),
    RouterModule.forRoot([
      { path: 'home', component: HomeComponent, pathMatch: 'full'}, 
      { path: '', component: HomeComponent},
      { path: 'reviewer', component: ReviewerArticlesComponent, canActivate: [RouteGuardService], data:{expectedRole:'ROLE_REVIEWER'}},
      { path: 'author', component: ArticleSearchComponent, canActivate: [RouteGuardService], data:{expectedRole:'ROLE_AUTOR'}},
      { path: 'reviewer/:id', component: ArticleReviewComponent, canActivate: [RouteGuardService], data:{expectedRole:'ROLE_REVIEWER'}},
      { path: 'author/:id', component: ReviewWrapperComponent, canActivate: [RouteGuardService], data:{expectedRole:'ROLE_AUTOR'}},
      { path: 'userr/:id', component: UserViewComponent, canActivate: [RouteGuardService], data:{expectedRole:'ROLE_REVIEWER'}},
      { path: 'usera/:id', component: UserViewComponent, canActivate: [RouteGuardService], data:{expectedRole:'ROLE_AUTOR'}}
    ]),
    AngularFireModule.initializeApp({
      apiKey: "AIzaSyClLu1OVmdBGfibByLFjuKd-oinPqwmgts",
      authDomain: "attempt2-7ac81.firebaseapp.com",
      storageBucket: "gs://attempt2-7ac81.appspot.com",
      databaseURL: "https://attempt2-7ac81.firebaseio.com",
      projectId: "attempt2-7ac81",
      messagingSenderId: "287123270237",
    appId: "1:287123270237:web:8305860c51685048"
    }),
    AngularFireStorageModule
  ],
  providers: [NgbActiveModal, {provide: APP_BASE_HREF, useValue: '/'}, RouteGuardService],
  bootstrap: [AppComponent],
  entryComponents: [LoginComponent,RegisterComponent],
  exports: [LoginComponent,RegisterComponent]
})
export class AppModule { }
