import { Component, OnInit } from '@angular/core';
import { KorisnikManagementService } from '../_services/korisnik-management/korisnik-management.service';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {
korisnici = [];
  constructor(public korisnikManagement: KorisnikManagementService) { }

  ngOnInit() {
    this.korisnici = [];
    this.refreshUsers();
  }
  async refreshUsers(){
    const data = await this.korisnikManagement.AllUsers();
    console.log(data);
    /*if (data!=undefined && data._embedded!=undefined)
    {
      this.korisnici=data._embedded.userEntities;
      console.log(this.korisnici);
    }
    else this.korisnici=[]
  }*/
  this.korisnici = data;
}

}
