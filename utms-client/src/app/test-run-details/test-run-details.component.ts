import { Component, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Project, Suite, TestRun } from '../models/project';
import { ProjectService } from '../project.service';

@Component({
  selector: 'app-test-run-details',
  templateUrl: './test-run-details.component.html',
  styleUrls: ['./test-run-details.component.css']
})
export class TestRunDetailsComponent implements OnInit {

  testRun?: TestRun;
  project?: Project;

  constructor(private projectService: ProjectService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    const projectIdFromRoute = String(routeParams.get('projectId'));
    const runIdFromRoute = Number(routeParams.get('runId'));


    // get that project
    this.projectService.projects.subscribe((projects) => {
      const project = projects.find((project) => project.id === projectIdFromRoute);
      this.project = project;
      const testRun = project?.testRuns.find((testRun) => testRun.runId === runIdFromRoute);

      this.testRun = testRun;
    });
  }

  computePassRate(testRun: TestRun): number {
    return this.projectService.computePassRate(testRun);
  }

  getNumberOfTests(testRun: TestRun): number {
    return this.projectService.getNumberOfTests(testRun);
  }

  toggleCarret(event: Event) {
    const carret = event.target as HTMLSpanElement;

    carret.parentElement?.querySelector(".nested")?.classList.toggle("active");
    carret.classList.toggle("caret-down");
  }

  decodeBase64(str: string) {
    return atob(str);
  }

  decodeBase64Error(str: string) {
    let result = this.decodeBase64(str); // error needs to be decoded 2 times for some reason
    return atob(result);
  }

  computeTimeTaken(start : Date, end : Date): number {
    let result: number = new Date(end).getTime() - new Date(start).getTime();
    console.log(start);
    console.log(end);
    return result;
  }

  computeSuiteTimeTaken(name: string) {
    const suite = this.testRun?.suites.find((suite) => suite.name === name);
    let result: number = 0;
    
    suite?.tests.forEach((test) => {
      result += this.computeTimeTaken(test.startTime, test.endTime);
    });

    return result;
  }
}
