import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Project, TestRun } from '../models/project';
import { ActivatedRoute } from '@angular/router';
import { ProjectService } from '../project.service';
import { setupTestingRouter } from '@angular/router/testing';
import { ɵangular_packages_platform_browser_dynamic_testing_testing_b } from '@angular/platform-browser-dynamic/testing';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.css']
})
export class ProjectDetailsComponent implements OnInit {

  project?: Project;

  constructor(private projectService: ProjectService,
    private route: ActivatedRoute) {

  }

  ngOnInit(): void {
    //extract project id
    const routeParams = this.route.snapshot.paramMap;
    const projectIdFromRoute = String(routeParams.get('projectId'))

    // get that project
    this.projectService.projects.subscribe((projects) => {
      this.project = projects.find((project) => project.id === projectIdFromRoute);
    })
  }

  computePassRate(testRun: TestRun): number {
    return this.projectService.computePassRate(testRun);
  }

  getNumberOfTests(testRun: TestRun): number {
    return this.projectService.getNumberOfTests(testRun);
  }

}
