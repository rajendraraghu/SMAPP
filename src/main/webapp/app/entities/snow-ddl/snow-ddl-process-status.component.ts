import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ISnowDDL } from 'app/shared/model/snow-ddl.model';
import { SnowDDLService } from 'app/entities/snow-ddl/snow-ddl.service';

@Component({
  selector: 'jhi-report',
  templateUrl: './snow-ddl-process-status.component.html'
})
export class SnowDDLProcessStatusComponent implements OnInit {
  snowDDL: ISnowDDL;
  reports: any;
  loading: boolean;
  constructor(private activatedRoute: ActivatedRoute, private snowDDLService: SnowDDLService, protected router: Router) {}

  ngOnInit() {
    this.loading = true;
    this.activatedRoute.data.subscribe(({ snowDDL }) => {
      this.snowDDL = snowDDL;
      this.refresh();
    });
  }

  refresh() {
    this.loading = true;
    this.snowDDLService.getReports(this.snowDDL.id).subscribe(response => {
      this.reports = response.body;
      this.loading = false;
    });
  }

  viewJobReport(batchId) {
    this.router.navigate(['/snow-ddl', 'history']);
  }

  previousState() {
    window.history.back();
  }
}
