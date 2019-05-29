import { Component, OnInit, HostListener } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm : FormGroup;
  submitted = false;
  constructor(private formBuilder: FormBuilder, 
  private activeModal: NgbActiveModal, private router: Router) { }
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

  @HostListener('onSubmit')
  onSubmit(){
    this.submitted = true;
    if(this.registerForm.invalid){
      return;
    }
  }

}
