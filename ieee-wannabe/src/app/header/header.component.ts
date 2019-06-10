import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LoginComponent } from '../login/login.component';
import { RegisterComponent } from '../register/register.component';
import { Router } from '@angular/router';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
loggedIn: boolean;
  constructor(private modalService: NgbModal, private router: Router) { 
    this.loggedIn = false;
  }

  ngOnInit() {
  }
  openLogin() {
    const modalRef = this.modalService.open(LoginComponent);
    modalRef.result.then((result) => {
      console.log(result);
      this.loggedIn = true;
    }).catch((error) => {
      console.log(error);
    });
  }
  logout() {
    this.loggedIn = false;
    localStorage.removeItem("id_token");
    console.log(localStorage.getItem("id_token"));
    this.router.navigate(['..']);
  }
  openRegister() {
    const modalRef = this.modalService.open(RegisterComponent);
    modalRef.result.then((result) => {
      console.log(result);
    }).catch((error) => {
      console.log(error);
    });
  }
}
