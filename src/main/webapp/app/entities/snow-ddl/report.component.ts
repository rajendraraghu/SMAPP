import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ISnowDDL } from 'app/shared/model/snow-ddl.model';
import { SnowDDLService } from 'app/entities/snow-ddl/snow-ddl.service';

@Component({
  selector: 'jhi-report',
  templateUrl: './report.component.html'
})
export class ReportComponent implements OnInit {
  snowDDL: ISnowDDL;
  reports: any;
  loading: boolean;
  constructor(private activatedRoute: ActivatedRoute, private snowDDLService: SnowDDLService) {}

  ngOnInit() {
    this.loading = true;
    this.activatedRoute.data.subscribe(({ snowDDL }) => {
      this.snowDDL = snowDDL;
      this.refresh();
    });
  }

  refresh() {
    this.loading = true;
    this.snowDDLService.getReports(this.snowDDL).subscribe(response => {
      this.reports = response.body;
      this.loading = false;
    });
  }

  previousState() {
    window.history.back();
  }
}
