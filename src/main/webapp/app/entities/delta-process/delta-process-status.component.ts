import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IDeltaProcess } from 'app/shared/model/delta-process.model';
import { DeltaProcessService } from 'app/entities/delta-process/delta-process.service';

@Component({
  selector: 'jhi-report',
  templateUrl: './delta-process-status.component.html'
})
export class DeltaProcessStatusComponent implements OnInit {
  deltaProcess: IDeltaProcess;
  reports: any;
  loading: boolean;
  constructor(private activatedRoute: ActivatedRoute, private deltaProcessService: DeltaProcessService, protected router: Router) {}

  ngOnInit() {
    this.loading = true;
    this.activatedRoute.data.subscribe(({ deltaProcess }) => {
      this.deltaProcess = deltaProcess;
      this.refresh();
    });
  }

  refresh() {
    this.loading = true;
    this.deltaProcessService.getProcessStatus(this.deltaProcess.id).subscribe(response => {
      this.reports = response.body;
      this.loading = false;
    });
  }

  viewJobReport(processId, batchId) {
    this.router.navigate(['/delta-process', processId, 'history', batchId, 'view']);
  }

  previousState() {
    window.history.back();
  }
}
