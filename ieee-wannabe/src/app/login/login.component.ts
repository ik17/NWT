import { Component, OnInit, HostListener } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { LoginUser } from '../_services/korisnik-management/loginUser';
import { KorisnikManagementService } from '../_services/korisnik-management/korisnik-management.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  Lu: any;
  something : Object;
  loginForm : FormGroup;
  submitted = false;
  badLogin : boolean;
  constructor(private formBuilder: FormBuilder, 
  private activeModal: NgbActiveModal, private router: Router, public korisnikManagement: KorisnikManagementService) { }

  ngOnInit() {
    localStorage.setItem("token","");
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }
  closeModal(){
    this.activeModal.close('Modal Closed');
  }
  get f(){
    return this.loginForm.controls;
  }
  async login(Lu:LoginUser) :Promise<any>{
    return this.korisnikManagement.login(this.Lu);
  }
  @HostListener('onSubmit')
  async onSubmit(){
    this.submitted = true;
    if(this.loginForm.invalid){
      return;
    }
    this.badLogin = false;
    this.Lu = new LoginUser(this.loginForm.get("username").value,this.loginForm.get("password").value,'', '', '', '');
    console.log(this.Lu);
    const data =  await this.login(this.Lu).catch((reason:any)=>{this.badLogin=true;});
    if(!this.badLogin){
    console.log(data.token);
    this.something = data;
    this.something.hasOwnProperty('token');
    console.log(this.something);
    localStorage.setItem('id_token', data.token.toString());
    console.log(localStorage.getItem("id_token"));
    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(data.token);
    console.log(decodedToken);
    this.closeModal();
    switch(decodedToken.role){
      case 'ROLE_REVIEWER':
        this.router.navigate(['../reviewer'])
        break;
      case 'ROLE_AUTOR':
        this.router.navigate(['../author'])
        break;
    }
  }
  else {

  }
}


}
