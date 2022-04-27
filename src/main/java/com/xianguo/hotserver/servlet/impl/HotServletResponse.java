package com.xianguo.hotserver.servlet.impl;

import com.xianguo.hotserver.bean.ResponseInfo;
import com.xianguo.hotserver.socket.response.HotResponseOutputStream;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * servlet返回实体
 * @author Xianguo
 */
@Slf4j
public class HotServletResponse extends ResponseInfo implements HttpServletResponse {

	@Override
	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
        log.info("method:getCharacterEncoding");
		return null;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub\
		log.info("method:getContentType");
		return null;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		log.info("method:getOutputStream");
		if(super.getHotResponseOutputStream() == null){
			super.setHotResponseOutputStream(new HotResponseOutputStream());
		}
		return super.getHotResponseOutputStream();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		// TODO Auto-generated method stub
        log.info("method:getWriter");
		return null;
	}

	@Override
	public void setCharacterEncoding(String charset) {
		// TODO Auto-generated method stub
        log.info("method:setCharacterEncoding");

	}

	@Override
	public void setContentLength(int len) {
		// TODO Auto-generated method stub
        log.info("method:setContentLength");

	}

	@Override
	public void setContentLengthLong(long len) {
		// TODO Auto-generated method stub
        log.info("method:setContentLengthLong");

	}

	@Override
	public void setContentType(String type) {
		// TODO Auto-generated method stub
        log.info("method:setContentType");

	}

	@Override
	public void setBufferSize(int size) {
		// TODO Auto-generated method stub
        log.info("method:setBufferSize");

	}

	@Override
	public int getBufferSize() {
		// TODO Auto-generated method stub
        log.info("method:getBufferSize");
		return 0;
	}

	@Override
	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub
        log.info("method:flushBuffer");

	}

	@Override
	public void resetBuffer() {
		// TODO Auto-generated method stub
        log.info("method:resetBuffer");

	}

	@Override
	public boolean isCommitted() {
		// TODO Auto-generated method stub
        log.info("method:isCommitted");
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
        log.info("method:reset");

	}

	@Override
	public void setLocale(Locale loc) {
		// TODO Auto-generated method stub
        log.info("method:setLocale");
		System.out.println(loc);
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
        log.info("method:getLocale");
		return null;
	}

	@Override
	public void addCookie(Cookie cookie) {

	}

	@Override
	public boolean containsHeader(String name) {
		return false;
	}

	@Override
	public String encodeURL(String url) {
		return null;
	}

	@Override
	public String encodeRedirectURL(String url) {
		return null;
	}

	@Override
	public String encodeUrl(String url) {
		return null;
	}

	@Override
	public String encodeRedirectUrl(String url) {
		return null;
	}

	@Override
	public void sendError(int sc, String msg) throws IOException {

	}

	@Override
	public void sendError(int sc) throws IOException {

	}

	@Override
	public void sendRedirect(String location) throws IOException {

	}

	@Override
	public void setDateHeader(String name, long date) {

	}

	@Override
	public void addDateHeader(String name, long date) {

	}

	@Override
	public void setHeader(String name, String value) {
		super.getResponseHead().put(name, value);
	}

	@Override
	public void addHeader(String name, String value) {
		super.getResponseHead().put(name, value);
	}

	@Override
	public void setIntHeader(String name, int value) {
		super.getResponseHead().put(name, value + "");
	}

	@Override
	public void addIntHeader(String name, int value) {
		super.getResponseHead().put(name, value + "");
	}

	@Override
	public void setStatus(int sc) {

	}

	@Override
	public void setStatus(int sc, String sm) {

	}

	@Override
	public int getStatus() {
		return 0;
	}

	@Override
	public String getHeader(String name) {
		return super.getResponseHead().get(name);
	}

	@Override
	public Collection<String> getHeaders(String name) {
		List<String> heads = new ArrayList<>();
		String head = super.getResponseHead().get(name);
		if(head != null){
			heads.add(head);
		}
		return heads;
	}

	@Override
	public Collection<String> getHeaderNames() {
		return null;
	}
}
