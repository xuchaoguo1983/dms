package cn.zmvision.ccm.bank.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.zmvision.ccm.bank.entity.DeviceLog;

public interface DeviceLogRepository extends JpaRepository<DeviceLog, Long>,
		JpaSpecificationExecutor<DeviceLog> {

}
