import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Project } from '../models/project';
import { ProjectService } from '../project.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  projects: Project[] = [];

  private subscription = new Subscription();

  constructor(private projectService: ProjectService,
    private router: Router) {

  }

  ngOnInit(): void {
    this.projectService.projects.subscribe((projects) => {
      this.projects = projects;
    })
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  getProjects() {
    return this.projects;
  }
}
