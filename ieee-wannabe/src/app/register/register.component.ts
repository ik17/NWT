import { Component, OnInit, HostListener } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { KorisnikManagementService } from '../_services/korisnik-management/korisnik-management.service';
import { LoginUser } from '../_services/korisnik-management/loginUser';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  Lu : any;
  registerForm : FormGroup;
  submitted = false;
  constructor(private formBuilder: FormBuilder, 
  private activeModal: NgbActiveModal, private router: Router, public korisnikManagement: KorisnikManagementService) { }
  //constructor() { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      ime: ['',Validators.required],
      prezime: ['', Validators.required],
      biografija: ['', Validators.required],
      rola: ['', Validators.required]
    });
  }
  closeModal(){
    this.activeModal.close('Modal Closed');
  }
  get f(){
    return this.registerForm.controls;
  }

  async register(Lu:LoginUser) {
    return this.korisnikManagement.register(this.Lu);
  }

  @HostListener('onSubmit')
  onSubmit(){
    this.submitted = true;
    if(this.registerForm.invalid){
      return;
    }
    this.Lu = new LoginUser(this.registerForm.get("username").value,
    this.registerForm.get("password").value,
    this.registerForm.get("biografija").value, 
    this.registerForm.get("ime").value, 
    this.registerForm.get("prezime").value, 
    this.registerForm.get("rola").value);
    console.log(this.Lu);
    const data =  this.register(this.Lu);
    console.log(data);
    
  }

}
