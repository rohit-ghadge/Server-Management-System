import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { Router } from '@angular/router'

@Component({
  selector: 'app-board-moderator',
  templateUrl: './board-moderator.component.html',
  styleUrls: ['./board-moderator.component.css']
})
export class BoardModeratorComponent implements OnInit {
  content?: string;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.userService.getModeratorBoard().subscribe(
      data => {
        this.content = data;
      },
      err => {
       // if anyone try to bypass login  then it will redirect to login page
        this.router.navigateByUrl('/login');
       // this.content = JSON.parse(err.error).message;
      }
    );
  }
}
