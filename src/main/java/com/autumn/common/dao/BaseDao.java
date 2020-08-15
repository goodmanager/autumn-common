package com.autumn.common.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.function.Function;

public abstract class BaseDao<T> {

	private final SqlSessionFactory sqlSessionFactory;

	public BaseDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	/**
	 * 获取T的列表
	 *
	 * @param statement dao接口定义的方法，全路径名称
	 * @param parameter 方法中的参数
	 * @return T类型的Flux
	 */
	protected Flux<T> applySelectList(String statement, Object parameter) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			if (parameter == null) {
				return Flux.fromIterable(session.selectList(statement));
			} else {
				return Flux.fromIterable(session.selectList(statement, parameter));
			}
		}
	}

	/**
	 * 获取单个T记录
	 *
	 * @param statement dao接口定义的方法，全路径名称
	 * @param parameter 方法中的参数
	 * @return T类型的Mono
	 */
	protected Mono<T> applySelectOne(String statement, Object parameter) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			if (parameter == null) {
				return Mono.justOrEmpty((T) session.selectOne(statement));
			} else {
				return Mono.justOrEmpty((T) session.selectOne(statement, parameter));
			}
		}
	}

	/**
	 * 获取记录数量
	 *
	 * @param statement dao接口定义的方法，全路径名称
	 * @param parameter dao接口定义的方法中的参数
	 * @return 记录数量
	 */
	protected Mono<Integer> applyCount(String statement, Object parameter) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			if (parameter == null) {
				return Mono.just(session.selectOne(statement));
			} else {
				return Mono.just(session.selectOne(statement, parameter));
			}
		}
	}

	/**
	 * 获取简单记录，根据主键获取
	 *
	 * @param statement dao接口定义的方法，全路径名称
	 * @param parameter dao接口定义的方法中的参数
	 * @return 一个T类型的对象
	 */
	protected T selectOne(String statement, @NotNull Object parameter) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.selectOne(statement, parameter);
		}
	}

	/**
	 * 插入记录
	 *
	 * @param statement dao接口定义的方法，全路径名称
	 * @param parameter dao接口定义的方法中的参数
	 * @return 插入成功的数量
	 */
	protected int insert(String statement, @NotNull Object parameter) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.insert(statement, parameter);
		}
	}

	/**
	 * 根据条件更新记录
	 *
	 * @param statement dao接口定义的方法，全路径名称
	 * @param parameter dao接口定义的方法中的参数
	 * @return 更新成功购得数量
	 */
	protected int update(String statement, @NotNull Object parameter) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.update(statement, parameter);
		}
	}

	/**
	 * 根据条件删除记录
	 *
	 * @param statement dao接口定义的方法，全路径名称
	 * @param parameter dao接口定义的方法中的参数
	 * @return 删除成功的数量
	 */
	protected int delete(String statement, @NotNull Object parameter) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.delete(statement, parameter);
		}
	}

	protected <R> Mono<R> apply(Function<SqlSession, R> function) {
		return Mono.just(applyFunction(function));
	}

	private <R> R applyFunction(Function<SqlSession, R> function) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return function.apply(session);
		}
	}

}
