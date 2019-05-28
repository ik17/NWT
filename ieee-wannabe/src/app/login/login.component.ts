import { Component, OnInit, HostListener } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm : FormGroup;
  submitted = false;
  constructor(private formBuilder: FormBuilder, 
  private activeModal: NgbActiveModal, private router: Router) { }

  ngOnInit() {
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

  @HostListener('onSubmit')
  onSubmit(){
    this.submitted = true;
    if(this.loginForm.invalid){
      return;
    }
  }


}
