export class LoginUser{
    username: string;
    password: string;
    biografija: string;
    ime: string;
    prezime: string;
    role: string;
    constructor(username, password, biografija, ime, prezime, role) {
        this.username = username;
        this.password = password;
        this.biografija = biografija;
        this.ime = ime;
        this.prezime = prezime;
        this.role = role;
    
    }
}