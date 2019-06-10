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
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule, 
    NgbModule, 
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule.forRoot([
      { path: 'home', component: HomeComponent, pathMatch: 'full'}, 
      { path: '', component: HomeComponent},
      { path: 'rewiever', component: ReviewerArticlesComponent, canActivate: [RouteGuardService], data:{expectedRole:'ROLE_REVIEWER'}},
      { path: 'author', component: ArticleSearchComponent, canActivate: [RouteGuardService], data:{expectedRole:'ROLE_AUTOR'}}
    ])
  ],
  providers: [NgbActiveModal, {provide: APP_BASE_HREF, useValue: '/'}, RouteGuardService],
  bootstrap: [AppComponent],
  entryComponents: [LoginComponent,RegisterComponent],
  exports: [LoginComponent,RegisterComponent]
})
export class AppModule { }
