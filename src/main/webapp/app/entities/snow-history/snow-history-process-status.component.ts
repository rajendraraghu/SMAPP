import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ISnowHistory } from 'app/shared/model/snow-history.model';
import { SnowHistoryService } from 'app/entities/snow-history/snow-history.service';

@Component({
  selector: 'jhi-report',
  templateUrl: './snow-history-process-status.component.html'
})
export class SnowHistoryProcessStatusComponent implements OnInit {
  snowHistory: ISnowHistory;
  reports: any;
  loading: boolean;
  constructor(private activatedRoute: ActivatedRoute, private snowHistoryService: SnowHistoryService, protected router: Router) {}

  ngOnInit() {
    this.loading = true;
    this.activatedRoute.data.subscribe(({ snowHistory }) => {
      this.snowHistory = snowHistory;
      this.refresh();
    });
  }

  refresh() {
    this.loading = true;
    this.snowHistoryService.getProcessStatus(this.snowHistory.id).subscribe(response => {
      this.reports = response.body;
      this.loading = false;
    });
  }

  viewJobStatus(processId, batchId) {
    this.router.navigate(['/snow-history', processId, 'history', batchId, 'view']);
  }

  previousState() {
    window.history.back();
  }
}
