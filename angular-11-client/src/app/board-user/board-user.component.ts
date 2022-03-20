import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css']
})
export class BoardUserComponent implements OnInit {
  content?: string;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.userService.getUserBoard().subscribe(
      data => {
        this.content = data;
      },
      err => {
        // if anyone try to bypass login  then it will redirect to login page
        this.router.navigateByUrl('/login');
        // this for Unauthorized message to user
        // this.content = JSON.parse(err.error).message;
      }
    );
  }
}
