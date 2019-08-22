import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ISnowParse } from 'app/shared/model/snow-parse.model';
import { SnowParseService } from 'app/entities/snow-parse/snow-parse.service';

@Component({
  selector: 'jhi-report',
  templateUrl: './snow-parse-process-status.component.html'
})
export class SnowParseProcessStatusComponent implements OnInit {
  snowParse: ISnowParse;
  reports: any;
  loading: boolean;
  constructor(private activatedRoute: ActivatedRoute, private snowParseService: SnowParseService, protected router: Router) {}

  ngOnInit() {
    this.loading = true;
    this.activatedRoute.data.subscribe(({ snowParse }) => {
      this.snowParse = snowParse;
      this.refresh();
    });
  }

  refresh() {
    this.loading = true;
    this.snowParseService.getProcessStatus(this.snowParse.id).subscribe(response => {
      this.reports = response.body;
      this.loading = false;
    });
  }

  viewJobStatus(processId, batchId) {
    this.router.navigate(['/snow-parse', processId, 'history', batchId, 'view']);
  }

  previousState() {
    window.history.back();
  }
}
